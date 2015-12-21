CREATE TABLE `rooms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_name` varchar(40) NOT NULL,
  `pc_one_number` int(11) NOT NULL,
  `pc_one_power_watts` double NOT NULL,
  `pc_two_number` int(11) NOT NULL,
  `pc_two_power_watts` double NOT NULL,
  `worked_hours` double NOT NULL,
  `energy_consumption` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rooms_id_uindex` (`id`),
  UNIQUE KEY `rooms_room_name_uindex` (`room_name`),
  UNIQUE KEY `rooms_pc_one_power_watts_uindex` (`pc_one_power_watts`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8