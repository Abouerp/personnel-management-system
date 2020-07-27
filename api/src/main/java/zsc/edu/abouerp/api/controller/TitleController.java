package zsc.edu.abouerp.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import zsc.edu.abouerp.common.entiry.ResultBean;
import zsc.edu.abouerp.entity.domain.Title;
import zsc.edu.abouerp.entity.vo.TitleVO;
import zsc.edu.abouerp.service.exception.TitleNotFoundException;
import zsc.edu.abouerp.service.mapper.TitleMapper;
import zsc.edu.abouerp.service.service.TitleService;

import java.util.Optional;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/title")
public class TitleController {

    private final TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    private static Title update(Title title, Optional<TitleVO> titleVO){
        titleVO.map(TitleVO::getName).ifPresent(title::setName);
        titleVO.map(TitleVO::getDescription).ifPresent(title::setDescription);
        titleVO.map(TitleVO::getRank).ifPresent(title::setRank);
        titleVO.map(TitleVO::getWage).ifPresent(title::setWage);
        return title;
    }

    @PostMapping
    public ResultBean<Title> save(@RequestBody TitleVO titleVO){
        return ResultBean.ok(titleService.save(TitleMapper.INSTANCE.toTitle(titleVO)));
    }

    @PutMapping("/{id}")
    public ResultBean<Title> update(
            @PathVariable Integer id,
            @RequestBody Optional<TitleVO> titleVO){
        Title title = titleService.findById(id).orElseThrow(TitleNotFoundException::new);
        return ResultBean.ok(titleService.save(update(title, titleVO)));
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Integer id){
        titleService.delete(id);
        return ResultBean.ok();
    }

    @GetMapping
    public ResultBean<Page<Title>> findAll(
            @PageableDefault Pageable pageable,
            TitleVO titleVO){
        return ResultBean.ok(titleService.findAll(titleVO,pageable));
    }

}
