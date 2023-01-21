package com.springboot.jpa.data.dao.impl;

import com.springboot.jpa.data.dao.ProductDAO;
import com.springboot.jpa.data.entity.Product;
import com.springboot.jpa.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

// 이 클래스를 스프링이 관리하는 빈으로 등록하려면 @Component 나 @Service 어노테이션 지정해야함
// 빈으로 등록된 객체는 다른 클래스가 인터페이스를 가지고 의존성을 주입받을 때 이 구현체를 찾아 주입함
@Component
public class ProductDAOImpl implements ProductDAO {
    // DAO 객체에서도 디비에 접근하기 위해 리포지토리 인터페이스를 사용해 의존성 주입을 받아야 함
    // 리포지토리를 정의 하고 생성자를 통해 의존성 주입을 받음
    private final ProductRepository productRepository;

    @Autowired
    public ProductDAOImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    // Product 엔티티를 db에 저장하는 기능
    @Override
    public Product insertProduct(Product product) {
        // save 는  jpa 메서드
        Product saveProduct = productRepository.save(product);
        return saveProduct;
    }

    @Override
    public Product selectProduct(Long number) {
        //getById JPA 문법
        Product SelectedProduct =productRepository.getById(number);
        return SelectedProduct;
    }

    @Override
    public Product updateProductName(Long number, String name) throws Exception {
        Optional<Product> selectedProduct = productRepository.findById(number);

        Product updateProduct;
        if (selectedProduct.isPresent()){
            Product product = selectedProduct.get();

            product.setName(name);
            product.setUpdatedAt(LocalDateTime.now());

            updateProduct = productRepository.save(product);
        }else{
            throw new Exception();
        }
        return updateProduct;
    }

    @Override
    public void deleteProduct(Long number) throws Exception {
        Optional<Product> selectedProduct = productRepository.findById(number);

        if (selectedProduct.isPresent()) {
            Product product = selectedProduct.get();
            productRepository.delete(product);
        } else{
            throw new Exception();
        }

    }
}
