package com.cricket.scorer.mapper;

import com.cricket.scorer.dto.InningsDTO;
import com.cricket.scorer.model.Innings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface InningsMapper {
    
    @Mapping(source = "match", target = "matchDTO")
    @Mapping(target = "battingTeamDTO", source = "battingTeam")
    @Mapping(target = "bowlingTeamDTO", source = "bowlingTeam")
    InningsDTO toDto(Innings innings);
    
    @Mapping(target = "match", source = "matchDTO")
    @Mapping(source = "battingTeamDTO", target = "battingTeam")
    @Mapping(source = "bowlingTeamDTO", target = "bowlingTeam")
    @Mapping(target = "overs", ignore = true)
    @Mapping(target = "balls", ignore = true)
    Innings toEntity(InningsDTO inningsDTO);
    
    List<InningsDTO> toDtoList(List<Innings> innings);
}
