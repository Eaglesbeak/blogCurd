package com.blog.service;

import java.util.List;

import com.blog.model.Diary;


public interface DiaryService {
	//д�ռ�
	void writediary(Diary diary);
	//����ȫ���ռ�
	List<Diary> selectAllDiary();
	//ɾ��
	void deleteDiaryById(int diaryid);
}
