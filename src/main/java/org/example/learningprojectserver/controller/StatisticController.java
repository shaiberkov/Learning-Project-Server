package org.example.learningprojectserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.entities.StudentStatisticsEntity;
import org.example.learningprojectserver.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.example.learningprojectserver.constants.ControllerConstants.UserStatistic.GET_USER_STATISTIC;
import static org.example.learningprojectserver.constants.ControllerConstants.UserStatistic.USER_STATISTIC_BASE_PATH;

@RestController
@RequestMapping(USER_STATISTIC_BASE_PATH)
@RequiredArgsConstructor
public class StatisticController {
  private final StatisticsService statisticsService;

    @GetMapping(GET_USER_STATISTIC)
    public StudentStatisticsEntity getUserStatistic(@RequestParam String userName) {
        System.out.println("getUserStatistic");
         return statisticsService.getUserStats(userName);
    }
}
