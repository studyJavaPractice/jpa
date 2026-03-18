package com.jpa.project_jpa.service;

import com.jpa.project_jpa.persitence.repository.PizzaPagSortRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
// import org.hibernate.query.Page;
// import org.springframework.data.domain.Page;
// import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
// import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Sort;
// import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.jpa.project_jpa.persitence.entity.PizzaEntity;
import com.jpa.project_jpa.persitence.repository.PizzaRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import com.jpa.project_jpa.service.dto.UpdatePizzaPriceDto;
import com.jpa.project_jpa.service.exception.EmailApiException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PizzaService {
    private final PizzaPagSortRepository pizzaPagSortRepository;
    // private final PizzaPagSortRepository pizzaPagSortRepository;
    private final PizzaRepository pizzaRepository;

    // @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        // this.pizzaPagSortRepository = pizzaPagSortRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

    public List<PizzaEntity> getAll() { 
        return this.pizzaRepository.findAll();
    }

    public Page<PizzaEntity> getAvailable(int page, int elements, String sortBy, String sortDirection) {
        System.out.println(this.pizzaRepository.countByVeganTrue());

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);

        return this.pizzaPagSortRepository.findByAvailableTrue(pageRequest);
    }

    public PizzaEntity getByName(String name) {
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("La pizza no existe"));
    }

    public List<PizzaEntity> getWith(String ingredient) {
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getWithout(String ingredient) {
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getCheapest(double price) {
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    public PizzaEntity get(int idPizza) {
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizza) {
        return this.pizzaRepository.save(pizza);
    }

    public void delete(int idPizza) {
        this.pizzaRepository.deleteById(idPizza);
    }

    @Transactional(noRollbackFor = EmailApiException.class)
    public void updatePrice(UpdatePizzaPriceDto dto) {
        this.pizzaRepository.updatePrice(dto);
        this.sendEmail();
    }

    private void sendEmail() {
        throw new EmailApiException();
    }


    public boolean exists(int idPizza) {
        return this.pizzaRepository.existsById(idPizza);
    }
}
