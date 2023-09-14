package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.NewsDetail;

@Repository
public interface NewsDetailRepository extends JpaRepository<NewsDetail, Long> {

}