package com.springboot.advanced_jpa.service.impl;

import com.springboot.advanced_jpa.data.dao.ProductDAO;
import com.springboot.advanced_jpa.data.dto.ProductDto;
import com.springboot.advanced_jpa.data.dto.ProductResponseDto;
import com.springboot.advanced_jpa.data.entity.Product;
import com.springboot.advanced_jpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;
    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
// 엔티티 -> dto 로 변환
    @Override
    public ProductResponseDto getProduct(Long number) {
        Product product = productDAO.selectProduct(number);
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumber(product.getNumber());
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setStock(product.getStock());
        return productResponseDto;
    }
// 전달받은 dto 객체를 통해 엔티티 객체를 생성해서 초기화한 후 dao 객체로 전달
    @Override
    public ProductResponseDto saveProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productDAO.insertProduct(product);
        // db 에 저장하면서 가져온 인덱스를 dto 에 담아 결과값으로 클라이언트에게 전달
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumber(savedProduct.getNumber());
        productResponseDto.setName(savedProduct.getName());
        productResponseDto.setPrice(savedProduct.getPrice());
        productResponseDto.setStock(savedProduct.getStock());

        return productResponseDto;
    }


    // 상품이름변경- 클라로부터 인덱스값과 이름 받아옴 + 기존이름도 받아와 인덱스 값의 상품정보와 일치하는지 점검 단계 추가도 가능
    @Override
    public ProductResponseDto changeProductName(Long number, String name) throws Exception {
        // 레코드의 이름 칼럼변경 - dao 에서 진행, 여기선 호출해서 결과값만 받아옴
        Product changeProduct = productDAO.updateProductName(number, name);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setNumber(changeProduct.getNumber());
        productResponseDto.setName(changeProduct.getName());
        productResponseDto.setPrice(changeProduct.getPrice());
        productResponseDto.setStock(changeProduct.getStock());
        return productResponseDto;
    }
// 삭제는 리턴받는 타입이 지정 x void
    @Override
    public void deleteProduct(Long number) throws Exception {
        productDAO.deleteProduct(number);

    }
}
