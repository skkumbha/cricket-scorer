from sqlalchemy import Column, Integer, Boolean, ForeignKey
from sqlalchemy.dialects.postgresql import UUID
from sqlalchemy.orm import relationship
from .base import Base


class MatchPlayer(Base):
    __tablename__ = 'match_players'

    id = Column(Integer, primary_key=True, autoincrement=True)
    match_id = Column(UUID(as_uuid=True), ForeignKey('matches.id', ondelete='CASCADE'))
    team_id = Column(Integer, ForeignKey('teams.id'))
    player_id = Column(Integer, ForeignKey('players.id'))
    is_playing = Column(Boolean, default=True)

    # Relationships
    match = relationship('Match', back_populates='match_players')
    team = relationship('Team', back_populates='match_players')
    player = relationship('Player', back_populates='match_players')
