ALTER TABLE `yozm-cafe`.member
    ADD COLUMN created_at datetime(6) not null DEFAULT NOW(6);
