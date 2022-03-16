package com.example.hero_service.services;

import com.example.common.clients.enemy.EnemyService;
import com.example.common.dto.Attributes;
import com.example.common.dto.EnemyDTO;
import com.example.common.dto.HeroDTO;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * HeroService класс сервсисного уровня,
 * который используется для совершения операций над обработанными запросами
 *
 * Пожалуйста, ознакомьтесь с обработчиками веб-запросов
 * {@link com.example.hero_service.controllers.HeroController}
 * для понимания возможностей API
 *
 * @author Artem
 *
 */
@Slf4j
@Service
public class HeroService {

    /** Rest-client for Enemy service */
    private final EnemyService enemyService;

    @Autowired
    public HeroService(final EnemyService enemyService) {
        this.enemyService = enemyService;
    }

    /**
     * Создаёт героя и отправляет запрос на создание оппонента.
     *
     * @param heroDto представление для создания объекта "Hero"
     * @return http-ответ, в теле которого находится информация о герое
     * @throws FeignException если не удаётся обратиться к сервису "Enemy"
     */
    public ResponseEntity<HeroDTO> createHero(final HeroDTO heroDto)  throws FeignException {

        // todo: some operation with entity and db

        // call Enemy creation method (controller)
        final EnemyDTO enemyDTO = this.enemyService
            .createEnemy(new EnemyDTO("Opponent_" + heroDto.getName(), Attributes.STRENGTH))
            .getBody();

        // test exception

        log.info("EnemyDTO from feign client: {}", enemyDTO);

        return ResponseEntity.ok(heroDto);
    }
}
