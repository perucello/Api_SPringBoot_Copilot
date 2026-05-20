package com.demo.service;

import com.demo.dto.ProdutoDTO;
import com.demo.model.Produto;
import com.demo.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarAtivos() {
        return produtoRepository.findByAtivoTrue()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isPresent()) {
            return converterParaDTO(produto.get());
        }
        throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
    }

    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorSku(String sku) {
        Optional<Produto> produto = produtoRepository.findBySku(sku);
        if (produto.isPresent()) {
            return converterParaDTO(produto.get());
        }
        throw new IllegalArgumentException("Produto não encontrado com SKU: " + sku);
    }

    @Transactional(readOnly = true)
    public List<ProdutoDTO> buscarPorNome(String nome) {
        return produtoRepository.buscarPorNome(nome)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProdutoDTO criar(ProdutoDTO produtoDTO) {
        validarProduto(produtoDTO);
        
        // Verificar se SKU já existe
        if (produtoDTO.getSku() != null && !produtoDTO.getSku().isEmpty()) {
            Optional<Produto> existente = produtoRepository.findBySku(produtoDTO.getSku());
            if (existente.isPresent()) {
                throw new IllegalArgumentException("Já existe um produto com esse SKU: " + produtoDTO.getSku());
            }
        }

        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDTO, produto);
        
        Produto produtoSalvo = produtoRepository.save(produto);
        return converterParaDTO(produtoSalvo);
    }

    @Transactional
    public ProdutoDTO atualizar(Long id, ProdutoDTO produtoDTO) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (!produtoExistente.isPresent()) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
        }

        validarProduto(produtoDTO);

        Produto produto = produtoExistente.get();
        
        // Não permite alterar SKU se já existe
        if (produtoDTO.getSku() != null && !produtoDTO.getSku().isEmpty() && 
            !produtoDTO.getSku().equals(produto.getSku())) {
            Optional<Produto> existente = produtoRepository.findBySku(produtoDTO.getSku());
            if (existente.isPresent()) {
                throw new IllegalArgumentException("Já existe um produto com esse SKU: " + produtoDTO.getSku());
            }
        }

        BeanUtils.copyProperties(produtoDTO, produto, "id", "dataCriacao");
        Produto produtoAtualizado = produtoRepository.save(produto);
        return converterParaDTO(produtoAtualizado);
    }

    @Transactional
    public void deletar(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        if (!produto.isPresent()) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }

    @Transactional
    public ProdutoDTO desativar(Long id) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (!produtoExistente.isPresent()) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
        }

        Produto produto = produtoExistente.get();
        produto.setAtivo(false);
        Produto produtoAtualizado = produtoRepository.save(produto);
        return converterParaDTO(produtoAtualizado);
    }

    @Transactional
    public ProdutoDTO ativar(Long id) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (!produtoExistente.isPresent()) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
        }

        Produto produto = produtoExistente.get();
        produto.setAtivo(true);
        Produto produtoAtualizado = produtoRepository.save(produto);
        return converterParaDTO(produtoAtualizado);
    }

    private void validarProduto(ProdutoDTO produtoDTO) {
        if (produtoDTO.getNome() == null || produtoDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (produtoDTO.getPreco() == null || produtoDTO.getPreco().signum() <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (produtoDTO.getQuantidade() == null || produtoDTO.getQuantidade() < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
    }

    private ProdutoDTO converterParaDTO(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        BeanUtils.copyProperties(produto, dto);
        return dto;
    }
}
