package com.application.nothing.repository;

        import com.application.nothing.model.ShoppingCart;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

        import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
        boolean existsByuser_userId(Long userId);
        Optional<ShoppingCart> findByUser_userId(Long userId);
}


