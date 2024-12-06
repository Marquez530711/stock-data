package com.ruxin.sd.service;

import com.ruxin.sd.source.BaseSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DataSchedulerService {

  private final BaseSource baseSource;

  public DataSchedulerService(@Qualifier("AKToolsSource") BaseSource baseSource) {
    this.baseSource = baseSource;
  }
}
