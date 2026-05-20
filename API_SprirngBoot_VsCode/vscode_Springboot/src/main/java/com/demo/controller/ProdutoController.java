package com.demo.controller;

import com.demo.dto.ProdutoDTO;
import com.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    /**
     * GET /api/produtos - Retorna todos os produtos
     */
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        try {
            List<ProdutoDTO> produtos = produtoService.listarTodos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/produtos/ativos - Retorna apenas produtos ativos
     */
    @GetMapping("/ativos")
    public ResponseEntity<List<ProdutoDTO>> listarAtivos() {
        try {
            List<ProdutoDTO> produtos = produtoService.listarAtivos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/produtos/{id} - Retorna um produto pelo ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
        try {
            ProdutoDTO produto = produtoService.buscarPorId(id);
            return ResponseEntity.ok(produto);
        } catch (IllegalArgumentException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/produtos/sku/{sku} - Retorna um produto pelo SKU
     */
    @GetMapping("/sku/{sku}")
    public ResponseEntity<Object> buscarPorSku(@PathVariable String sku) {
        try {
            ProdutoDTO produto = produtoService.buscarPorSku(sku);
            return ResponseEntity.ok(produto);
        } catch (IllegalArgumentException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/produtos/buscar?nome=xxx - Busca produtos pelo nome
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ProdutoDTO>> buscarPorNome(@RequestParam String nome) {
        try {
            List<ProdutoDTO> produtos = produtoService.buscarPorNome(nome);
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /api/produtos - Cria um novo produto
     */
    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody ProdutoDTO produtoDTO) {
        try {
            ProdutoDTO novoProduto = produtoService.criar(produtoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
        } catch (IllegalArgumentException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PUT /api/produtos/{id} - Atualiza um produto
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        try {
            ProdutoDTO produtoAtualizado = produtoService.atualizar(id, produtoDTO);
            return ResponseEntity.ok(produtoAtualizado);
        } catch (IllegalArgumentException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * DELETE /api/produtos/{id} - Deleta um produto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable Long id) {
        try {
            produtoService.deletar(id);
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", "Produto deletado com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PATCH /api/produtos/{id}/desativar - Desativa um produto
     */
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Object> desativar(@PathVariable Long id) {
        try {
            ProdutoDTO produtoDesativado = produtoService.desativar(id);
            return ResponseEntity.ok(produtoDesativado);
        } catch (IllegalArgumentException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * PATCH /api/produtos/{id}/ativar - Ativa um produto
     */
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Object> ativar(@PathVariable Long id) {
        try {
            ProdutoDTO produtoAtivado = produtoService.ativar(id);
            return ResponseEntity.ok(produtoAtivado);
        } catch (IllegalArgumentException e) {
            Map<String, String> erro = new HashMap<>();
            erro.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /api/produtos/health - Health check
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("mensagem", "API de Produtos está rodando");
        return ResponseEntity.ok(response);
    }
}
