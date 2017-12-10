package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.Archive;

public interface ArchiveRepository extends JpaRepository<Archive, Integer>{

}
