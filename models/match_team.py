from sqlalchemy import Column, Integer, ForeignKey
from sqlalchemy.dialects.postgresql import UUID
from sqlalchemy.orm import relationship
from .base import Base


class MatchTeam(Base):
    __tablename__ = 'match_teams'

    id = Column(Integer, primary_key=True, autoincrement=True)
    match_id = Column(UUID(as_uuid=True), ForeignKey('matches.id', ondelete='CASCADE'))
    team_id = Column(Integer, ForeignKey('teams.id'))
    innings_number = Column(Integer)

    # Relationships
    match = relationship('Match', back_populates='match_teams')
    team = relationship('Team', back_populates='match_teams')
