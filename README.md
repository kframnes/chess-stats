# ChessStats
A system for importing (from pgn) and reporting on Chess move statistics.

# Modes

### Schema
```
create table games
(
	id int auto_increment,
	site enum('CHESS_COM', 'LICHESS_ORG') not null,
	game_key varchar(255) not null,
	type enum('BLITZ', 'RAPID', 'CLASSICAL') not null,
	white_player varchar(50) not null,
	black_player varchar(50) not null,
	white_elo int null,
	black_elo int null,
  	result enum('WHITE_WIN', 'BLACK_WIN', 'DRAW') not null,
	constraint games_pk
		primary key (id)
);

create unique index games__ux
	on games (game_key);
	
create index games__black_index
	on games (black_player, black_elo);

create index games__white_index
	on games (white_player, white_elo);

create table moves
(
	id int auto_increment,
	game_id int not null,
	move_color enum('WHITE', 'BLACK') not null,
	ply int not null,
	move varchar(10) not null comment 'SAN representation of this move',
	eval_before float not null comment 'position eval before this move',
	mate_in_before int null comment 'there a mate-in-N before this move', 
	eval_after float not null comment 'position eval after this move',
	mate_in_after int null comment 'there a mate-in-N after this move',
  	move_rank int null comment 'what engine move was this; null if not recommended by engine',
  	forced boolean not null comment 'was the move considered forcing; within engine recommendation, but only option',
  	final_position boolean not null,
  	checkmate boolean not null,
	constraint moves_pk
		primary key (id)
);

create index moves__game_id
    on moves (game_id);
    
create table cheaters
(
	id int auto_increment,
	player_name varchar(50) not null,
	site enum('CHESS_COM', 'LICHESS_ORG') not null,
	flagged boolean not null,
	constraint cheaters_pk
		primary key (id)
);

create unique index cheaters_player_name_site_uindex
	on cheaters (player_name, site);
```

### Import
Import moves and store statistics.

```
Importing 3/50 games            [===            ] 15%
------------------------------------------------------------
keithframnes - random player    [===============] 100%
keithframnes - random player    [===========    ] 60%
keithframnes - random player    [========       ] 58%
```

### Report
Query for relevant cross-section of data and display report.

Reports are generated with two levels of break-down: (1) all games, all wins, all loses first; (2) vs elo, position eval, etc. second.

```
================================================================================================================
= Book Moves: 5                                                                                                =
= Comp Elo: 1063-1163                                                                                          =
================================================================================================================
Position Eval                           N                   T1%                T2%                T3%                
================================================================================================================
...
...
...
Even +/- 50 CP                          213   (7420  )      34.27  (35.44)     49.77  (53.72)     61.50  (65.15)     
...
...
...
        ——————————————0.95   ( 0.63)———————————————▶        ——————————————1.41   ( 1.08)———————————————▶
LOSING                                                EVEN                                               WINNING
        ◀—————————————8.84   (12.39)————————————————        ◀—————————————0.00   ( 0.00)————————————————
...
...
...
================================================================================================================
Position Eval                           N                   ▼ 100-299 CP       ▼ 300-499 CP       ▼ 500+ CP                  
================================================================================================================
...
...
...
Even +/- 50 CP                          213   (7420  )      34.27  (35.44)     49.77  (53.72)     61.50  (65.15)
...
...
...
```
