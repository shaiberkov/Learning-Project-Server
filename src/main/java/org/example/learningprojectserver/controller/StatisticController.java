package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.entities.UserStatisticsEntity;
import org.example.learningprojectserver.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("Learning-App/Statistic")
public class StatisticController {
  private final StatisticsService statisticsService;
 @Autowired
  public StatisticController(StatisticsService statisticsService) {
      this.statisticsService = statisticsService;
  }

    @GetMapping("/get-User-Statistic")
    public UserStatisticsEntity getUserStatistic(  @RequestParam String userName) {
         return statisticsService.getUserStats(userName);
    }
}
