package com.springRestful.banco_api.controller.dto;

import com.springRestful.banco_api.domain.model.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public record UserDto(
        Long id,
        String name,
        AccountDto account,
        CardDto card,
        List<FeatureDto> features,
        List<NewsDto> news) {

    public UserDto(User model){
        this(
                model.getId(),
                model.getName(),
                ofNullable(model.getAccount()).map(AccountDto::new).orElse(null),
                ofNullable(model.getCard()).map(CardDto::new).orElse(null),
                ofNullable(model.getFeatures()).orElse(Collections.emptyList()).stream().map(FeatureDto::new).collect(Collectors.toList()),
                ofNullable(model.getNews()).orElse(Collections.emptyList()).stream().map(NewsDto::new).collect(Collectors.toList())
        );
    }

    public User toModel() {
        User model = new User();
        model.setId(this.id);
        model.setName(this.name);
        model.setAccount(ofNullable(this.account).map(AccountDto::toModel).orElse(null));
        model.setCard(ofNullable(this.card).map(CardDto::toModel).orElse(null));
        model.setFeatures(ofNullable(this.features).orElse(Collections.emptyList()).stream().map(FeatureDto::toModel).collect(Collectors.toList()));
        model.setNews(ofNullable(this.news).orElse(Collections.emptyList()).stream().map(NewsDto::toModel).collect(Collectors.toList()));
        return model;
    }
}
