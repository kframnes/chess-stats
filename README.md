# ChessStats
A system for importing (from pgn) and reporting on Chess move statistics.

### Schema
```
create table if not exists games
(
	id int auto_increment
		primary key,
	site enum('CHESS_COM', 'LICHESS_ORG') not null,
	game_key varchar(255) not null,
	type enum('BLITZ', 'RAPID', 'CLASSICAL') not null,
	white_player int not null,
	black_player int not null,
	result enum('WHITE_WIN', 'BLACK_WIN', 'DRAW') not null,
	constraint games__ux
		unique (game_key)
);

create table if not exists moves
(
	id int auto_increment
		primary key,
	game_id int not null,
	move_color enum('WHITE', 'BLACK') not null,
	ply int not null,
	move varchar(10) not null comment 'SAN representation of this move',
	eval_before float not null comment 'position eval before this move',
	mate_in_before int null comment 'there a mate-in-N before this move',
	eval_after float not null comment 'position eval after this move',
	mate_in_after int null comment 'there a mate-in-N after this move',
	move_rank int null comment 'what engine move was this; null if not recommended by engine',
	forced tinyint(1) not null comment 'was the move considered forcing; within engine recommendation, but only option',
	final_position tinyint(1) not null,
	checkmate tinyint(1) not null
);

create index moves__game_id
	on moves (game_id);

create table if not exists players
(
	id int auto_increment
		primary key,
	site enum('CHESS_COM', 'LICHESS_ORG') not null,
	username varchar(50) not null,
	elo int null,
	dirty tinyint(1) default 1 not null,
	accused tinyint(1) default 0 not null,
	flagged tinyint(1) default 0 not null,
	constraint players_site_username_uindex
		unique (site, username)
);

create table if not exists players_stats
(
	id int auto_increment
		primary key,
	player_id int not null,
	n_losing_mate int default 0 null,
	n_losing_1000 int default 0 null,
	n_losing_800 int default 0 null,
	n_losing_600 int default 0 null,
	n_losing_400 int default 0 null,
	n_losing_200 int default 0 null,
	n_losing_50 int default 0 null,
	n_even int default 0 null,
	n_winning_50 int default 0 null,
	n_winning_200 int default 0 null,
	n_winning_400 int default 0 null,
	n_winning_600 int default 0 null,
	n_winning_800 int default 0 null,
	n_winning_1000 int default 0 null,
	n_winning_mate int default 0 null,
	n_winning int default 0 null,
	n_losing int default 0 null,
	n_total int default 0 null,
	t1_losing_mate float(6,2) default 0.00 null,
	t1_losing_1000 float(6,2) default 0.00 null,
	t1_losing_800 float(6,2) default 0.00 null,
	t1_losing_600 float(6,2) default 0.00 null,
	t1_losing_400 float(6,2) default 0.00 null,
	t1_losing_200 float(6,2) default 0.00 null,
	t1_losing_50 float(6,2) default 0.00 null,
	t1_even float(6,2) default 0.00 null,
	t1_winning_50 float(6,2) default 0.00 null,
	t1_winning_200 float(6,2) default 0.00 null,
	t1_winning_400 float(6,2) default 0.00 null,
	t1_winning_600 float(6,2) default 0.00 null,
	t1_winning_800 float(6,2) default 0.00 null,
	t1_winning_1000 float(6,2) default 0.00 null,
	t1_winning_mate float(6,2) default 0.00 null,
	t1_winning float(6,2) default 0.00 null,
	t1_losing float(6,2) default 0.00 null,
	t1_total float(6,2) default 0.00 null,
	t2_losing_mate float(6,2) default 0.00 null,
	t2_losing_1000 float(6,2) default 0.00 null,
	t2_losing_800 float(6,2) default 0.00 null,
	t2_losing_600 float(6,2) default 0.00 null,
	t2_losing_400 float(6,2) default 0.00 null,
	t2_losing_200 float(6,2) default 0.00 null,
	t2_losing_50 float(6,2) default 0.00 null,
	t2_even float(6,2) default 0.00 null,
	t2_winning_50 float(6,2) default 0.00 null,
	t2_winning_200 float(6,2) default 0.00 null,
	t2_winning_400 float(6,2) default 0.00 null,
	t2_winning_600 float(6,2) default 0.00 null,
	t2_winning_800 float(6,2) default 0.00 null,
	t2_winning_1000 float(6,2) default 0.00 null,
	t2_winning_mate float(6,2) default 0.00 null,
	t2_winning float(6,2) default 0.00 null,
	t2_losing float(6,2) default 0.00 null,
	t2_total float(6,2) default 0.00 null,
	t3_losing_mate float(6,2) default 0.00 null,
	t3_losing_1000 float(6,2) default 0.00 null,
	t3_losing_800 float(6,2) default 0.00 null,
	t3_losing_600 float(6,2) default 0.00 null,
	t3_losing_400 float(6,2) default 0.00 null,
	t3_losing_200 float(6,2) default 0.00 null,
	t3_losing_50 float(6,2) default 0.00 null,
	t3_even float(6,2) default 0.00 null,
	t3_winning_50 float(6,2) default 0.00 null,
	t3_winning_200 float(6,2) default 0.00 null,
	t3_winning_400 float(6,2) default 0.00 null,
	t3_winning_600 float(6,2) default 0.00 null,
	t3_winning_800 float(6,2) default 0.00 null,
	t3_winning_1000 float(6,2) default 0.00 null,
	t3_winning_mate float(6,2) default 0.00 null,
	t3_winning float(6,2) default 0.00 null,
	t3_losing float(6,2) default 0.00 null,
	t3_total float(6,2) default 0.00 null,
	givesMajors_losing_mate float(6,2) default 0.00 null,
	givesMajors_losing_1000 float(6,2) default 0.00 null,
	givesMajors_losing_800 float(6,2) default 0.00 null,
	givesMajors_losing_600 float(6,2) default 0.00 null,
	givesMajors_losing_400 float(6,2) default 0.00 null,
	givesMajors_losing_200 float(6,2) default 0.00 null,
	givesMajors_losing_50 float(6,2) default 0.00 null,
	givesMajors_even float(6,2) default 0.00 null,
	givesMajors_winning_50 float(6,2) default 0.00 null,
	givesMajors_winning_200 float(6,2) default 0.00 null,
	givesMajors_winning_400 float(6,2) default 0.00 null,
	givesMajors_winning_600 float(6,2) default 0.00 null,
	givesMajors_winning_800 float(6,2) default 0.00 null,
	givesMajors_winning_1000 float(6,2) default 0.00 null,
	givesMajors_winning_mate float(6,2) default 0.00 null,
	givesMajors_winning float(6,2) default 0.00 null,
	givesMajors_losing float(6,2) default 0.00 null,
	givesMajors_total float(6,2) default 0.00 null,
	givesMinors_losing_mate float(6,2) default 0.00 null,
	givesMinors_losing_1000 float(6,2) default 0.00 null,
	givesMinors_losing_800 float(6,2) default 0.00 null,
	givesMinors_losing_600 float(6,2) default 0.00 null,
	givesMinors_losing_400 float(6,2) default 0.00 null,
	givesMinors_losing_200 float(6,2) default 0.00 null,
	givesMinors_losing_50 float(6,2) default 0.00 null,
	givesMinors_even float(6,2) default 0.00 null,
	givesMinors_winning_50 float(6,2) default 0.00 null,
	givesMinors_winning_200 float(6,2) default 0.00 null,
	givesMinors_winning_400 float(6,2) default 0.00 null,
	givesMinors_winning_600 float(6,2) default 0.00 null,
	givesMinors_winning_800 float(6,2) default 0.00 null,
	givesMinors_winning_1000 float(6,2) default 0.00 null,
	givesMinors_winning_mate float(6,2) default 0.00 null,
	givesMinors_winning float(6,2) default 0.00 null,
	givesMinors_losing float(6,2) default 0.00 null,
	givesMinors_total float(6,2) default 0.00 null,
	givesPawns_losing_mate float(6,2) default 0.00 null,
	givesPawns_losing_1000 float(6,2) default 0.00 null,
	givesPawns_losing_800 float(6,2) default 0.00 null,
	givesPawns_losing_600 float(6,2) default 0.00 null,
	givesPawns_losing_400 float(6,2) default 0.00 null,
	givesPawns_losing_200 float(6,2) default 0.00 null,
	givesPawns_losing_50 float(6,2) default 0.00 null,
	givesPawns_even float(6,2) default 0.00 null,
	givesPawns_winning_50 float(6,2) default 0.00 null,
	givesPawns_winning_200 float(6,2) default 0.00 null,
	givesPawns_winning_400 float(6,2) default 0.00 null,
	givesPawns_winning_600 float(6,2) default 0.00 null,
	givesPawns_winning_800 float(6,2) default 0.00 null,
	givesPawns_winning_1000 float(6,2) default 0.00 null,
	givesPawns_winning_mate float(6,2) default 0.00 null,
	givesPawns_winning float(6,2) default 0.00 null,
	givesPawns_losing float(6,2) default 0.00 null,
	givesPawns_total float(6,2) default 0.00 null,
	constraint players_stats__ux
		unique (player_id)
);


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

### Clean
Pre-calculate player stats based on imported games.  This is used to streamline the reporting process.
```
     UI IN PROGRESS
```

### Report
Query for relevant cross-section of data and display report.

We present two different kinds of measurement for players:
(1) T-analysis determining, based on the engine configuration, what percentage of moves were the top, top two, or top
    three engine moves.
(2) CP loss determining, based on the engine configuration, what percentage of moves lost a pawns worth, a minor 
    pieces worth, or a major pieces worth of advantage.

Both of these measurements are given in several cross-sections defined by the moving players advantage in the position
the move was made in.  For every measurement we also offer a comparison between the players measurement and the
measurement of their peers in the same cross-section.  We display, in parenthesis, the mean of all peers measurement
and also highlight the entire measurement based on how out-of-line the reported players measurement was based on
standard deviations off the given mean:
(1) highlighted bright white indicates the player means the player under-performed their peers
(2) highlighted bright green indicates the player over-performed their peers by less than a single standard deviation
(3) highlighted bright yellow indicates the player over-performed their peers by between one and 
    two standard deviations
(4) highlighted bright red indicates the player over-performed their peers by three standard deviations or more
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
