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
    
    @Mapping(source = "match.id", target = "matchId")
    @Mapping(source = "battingTeam.id", target = "battingTeamId")
    @Mapping(source = "bowlingTeam.id", target = "bowlingTeamId")
    InningsDTO toDto(Innings innings);
    
    @Mapping(target = "match", ignore = true)
    @Mapping(target = "battingTeam", ignore = true)
    @Mapping(target = "bowlingTeam", ignore = true)
    @Mapping(target = "overs", ignore = true)
    @Mapping(target = "balls", ignore = true)
    Innings toEntity(InningsDTO inningsDTO);
    
    List<InningsDTO> toDtoList(List<Innings> innings);
}
