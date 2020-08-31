package zsc.edu.abouerp.service.service;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zsc.edu.abouerp.entity.domain.QTitle;
import zsc.edu.abouerp.entity.domain.Title;
import zsc.edu.abouerp.entity.vo.TitleVO;
import zsc.edu.abouerp.service.repository.TitleRepository;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class TitleService {

    private final TitleRepository titleRepository;

    public TitleService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public Title save(Title title) {
        return titleRepository.save(title);
    }

    public Optional<Title> findById(Integer id) {
        return titleRepository.findById(id);
    }

    public void delete(Integer id) {
        titleRepository.deleteById(id);
    }

    public Page<Title> findAll(TitleVO titleVO, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QTitle qTitle = QTitle.title;
        if (titleVO != null && titleVO.getName() != null && !titleVO.getName().isEmpty()) {
            booleanBuilder.and(qTitle.name.containsIgnoreCase(titleVO.getName()));
        }
        if (titleVO != null && titleVO.getRank() != null && !titleVO.getRank().isEmpty()) {
            booleanBuilder.and(qTitle.rank.containsIgnoreCase(titleVO.getRank()));
        }
        if (titleVO != null && titleVO.getDescription() != null && !titleVO.getDescription().isEmpty()) {
            booleanBuilder.and(qTitle.description.containsIgnoreCase(titleVO.getDescription()));
        }
        if (titleVO != null && titleVO.getWage() != null) {
            booleanBuilder.and(qTitle.wage.between(0, titleVO.getWage()));
        }
        return titleRepository.findAll(booleanBuilder, pageable);
    }

}
