package com.cricket.scorer.mapper;

import com.cricket.scorer.dto.BallDTO;
import com.cricket.scorer.model.Ball;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BallMapper {
    
    @Mapping(source = "over.id", target = "overId")
    @Mapping(source = "innings.id", target = "inningsId")
    @Mapping(source = "batsman.id", target = "batsmanId")
    @Mapping(source = "bowler.id", target = "bowlerId")
    @Mapping(source = "fielder.id", target = "fielderId")
    BallDTO toDto(Ball ball);
    
    @Mapping(target = "over", ignore = true)
    @Mapping(target = "innings", ignore = true)
    @Mapping(target = "batsman", ignore = true)
    @Mapping(target = "bowler", ignore = true)
    @Mapping(target = "fielder", ignore = true)
    Ball toEntity(BallDTO ballDTO);
    
    List<BallDTO> toDtoList(List<Ball> balls);
}
