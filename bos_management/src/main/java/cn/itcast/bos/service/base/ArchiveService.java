package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Archive;
import cn.itcast.bos.domain.base.SubArchive;

public interface ArchiveService {

	void saveArchive(Archive archive);

	void updateArchive(Archive archive);

	void deleteArchive(Integer id);

	Page<Archive> findPageData(Pageable pageable);

	void saveSubArchive(SubArchive subArchive);

	void updateSubArchive(SubArchive subArchive);

	void deleteSubArchive(Integer id);

	Page<SubArchive> findSubPageData(Pageable pageable);
}
