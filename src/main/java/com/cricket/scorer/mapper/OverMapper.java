package com.cricket.scorer.mapper;

import com.cricket.scorer.dto.OverDTO;
import com.cricket.scorer.model.Over;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OverMapper {
    
    // Map the full Innings -> InningsDTO (OverDTO defines inningsDTO property)
    @Mapping(source = "innings", target = "inningsDTO")
    @Mapping(source = "bowler.id", target = "bowlerId")
    OverDTO toDto(Over over);
    
    @Mapping(target = "innings", source = "inningsDTO")
    @Mapping(target = "bowler", ignore = true)
    @Mapping(target = "balls", ignore = true)
    Over toEntity(OverDTO overDTO);
    
    List<OverDTO> toDtoList(List<Over> overs);
}
