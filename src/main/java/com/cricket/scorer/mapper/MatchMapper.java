package com.cricket.scorer.mapper;

import com.cricket.scorer.dto.MatchDTO;
import com.cricket.scorer.model.Match;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MatchMapper {
    
    MatchDTO toDto(Match match);
    
    //Match toEntity(MatchDTO matchDTO);
    
    List<MatchDTO> toDtoList(List<Match> matches);
    
    void updateEntityFromDto(MatchDTO matchDTO, @MappingTarget Match match);
}
