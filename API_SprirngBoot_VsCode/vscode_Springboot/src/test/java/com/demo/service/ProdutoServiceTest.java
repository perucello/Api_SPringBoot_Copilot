package com.demo.service;

import com.demo.dto.ProdutoDTO;
import com.demo.model.Produto;
import com.demo.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;
    private ProdutoDTO produtoDTO;

    @BeforeEach
    public void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Notebook Test");
        produto.setDescricao("Notebook para teste");
        produto.setPreco(new BigDecimal("3500.00"));
        produto.setQuantidade(5);
        produto.setSku("NOTEBOOK-TEST");
        produto.setAtivo(true);
        produto.setDataCriacao(LocalDateTime.now());
        produto.setDataAtualizacao(LocalDateTime.now());

        produtoDTO = new ProdutoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNome("Notebook Test");
        produtoDTO.setDescricao("Notebook para teste");
        produtoDTO.setPreco(new BigDecimal("3500.00"));
        produtoDTO.setQuantidade(5);
        produtoDTO.setSku("NOTEBOOK-TEST");
        produtoDTO.setAtivo(true);
    }

    @Test
    public void testBuscarPorIdSucesso() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoDTO result = produtoService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals("Notebook Test", result.getNome());
        verify(produtoRepository, times(1)).findById(1L);
    }

    @Test
    public void testBuscarPorIdNaoEncontrado() {
        when(produtoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            produtoService.buscarPorId(999L);
        });

        verify(produtoRepository, times(1)).findById(999L);
    }

    @Test
    public void testCriarProdutoSucesso() {
        when(produtoRepository.findBySku("NOTEBOOK-TEST")).thenReturn(Optional.empty());
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        ProdutoDTO result = produtoService.criar(produtoDTO);

        assertNotNull(result);
        assertEquals("Notebook Test", result.getNome());
        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    public void testCriarProdutoSkuDuplicado() {
        when(produtoRepository.findBySku("NOTEBOOK-TEST")).thenReturn(Optional.of(produto));

        assertThrows(IllegalArgumentException.class, () -> {
            produtoService.criar(produtoDTO);
        });

        verify(produtoRepository, never()).save(any(Produto.class));
    }

    @Test
    public void testValidarProdutoNomeVazio() {
        produtoDTO.setNome("");

        assertThrows(IllegalArgumentException.class, () -> {
            produtoService.criar(produtoDTO);
        });
    }

    @Test
    public void testValidarProdutoPrecoZero() {
        produtoDTO.setPreco(BigDecimal.ZERO);

        assertThrows(IllegalArgumentException.class, () -> {
            produtoService.criar(produtoDTO);
        });
    }
}
