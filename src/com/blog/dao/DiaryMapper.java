package com.blog.dao;

import java.util.List;

import com.blog.model.Diary;


public interface DiaryMapper {
	//д�ռ�
	void writediary(Diary diary);
	//����ȫ���ռ�
	List<Diary> selectAllDiary();
	//ɾ��
	void deleteDiaryById(int diaryid);
}
