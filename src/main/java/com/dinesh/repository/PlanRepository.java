package com.dinesh.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dinesh.entity.PlanEntity;

public interface PlanRepository extends JpaRepository<PlanEntity, Serializable>{

}