from sqlalchemy import Column, Integer, Text, TIMESTAMP, func
from sqlalchemy.dialects.postgresql import UUID
from sqlalchemy.orm import relationship
from .base import Base


class Match(Base):
    __tablename__ = 'matches'

    id = Column(UUID(as_uuid=True), primary_key=True)
    match_name = Column(Text)
    match_type = Column(Text, default='Friendly')
    overs = Column(Integer, nullable=False)
    balls_per_over = Column(Integer, default=6)
    location = Column(Text)
    start_time = Column(TIMESTAMP, default=func.now())
    status = Column(Text, default='setup')

    # Relationships
    match_teams = relationship('MatchTeam', back_populates='match', cascade='all, delete-orphan')
    match_players = relationship('MatchPlayer', back_populates='match', cascade='all, delete-orphan')
    innings = relationship('Innings', back_populates='match', cascade='all, delete-orphan')
    match_accesses = relationship('MatchAccess', back_populates='match', cascade='all, delete-orphan')
    scorer_requests = relationship('ScorerRequest', back_populates='match', cascade='all, delete-orphan')
    fixtures = relationship('Fixture', back_populates='match')
    match_awards = relationship('MatchAward', back_populates='match')
