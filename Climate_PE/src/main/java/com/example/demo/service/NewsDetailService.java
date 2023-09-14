package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.NewsDetail;
import com.example.demo.repository.NewsDetailRepository;

@Service
public class NewsDetailService {
	private final NewsDetailRepository newsDetailRepository;

	@Autowired
	public NewsDetailService(NewsDetailRepository exampleEntityRepository) {
		this.newsDetailRepository = exampleEntityRepository;
	}

	public NewsDetail getNewsDetail(Long id) {
		Optional<NewsDetail> optionalNewsDetail = newsDetailRepository.findById(id);
		return optionalNewsDetail.orElse(null);
	}

	public List<NewsDetail> getAllNewsDetails() {
		return newsDetailRepository.findAll();
	}

	public NewsDetail createNewsDetail(NewsDetail newsDetail) {
		return newsDetailRepository.save(newsDetail);
	}

	public NewsDetail updateNewsDetail(NewsDetail updatedNewsDetail) {
		return newsDetailRepository.save(updatedNewsDetail);
	}

	public void deleteNewsDetail(Long id) {
		newsDetailRepository.deleteById(id);
	}

	public List<NewsDetail> getRandomNews() {
		List<NewsDetail> allNews = newsDetailRepository.findAll();
		int totalNewsCount = allNews.size();
		int numberOfRandomNews = 3;

		List<NewsDetail> randomNews = new ArrayList<>();
		Random random = new Random();

		if (totalNewsCount <= numberOfRandomNews) {
			return allNews;
		}

		while (randomNews.size() < numberOfRandomNews) {
			NewsDetail randomEntity = allNews.get(random.nextInt(totalNewsCount));
			if (!randomNews.contains(randomEntity)) {
				randomNews.add(randomEntity);
			}
		}

		return randomNews;
	}
}