ALTER TABLE `yozm-cafe`.member
    ADD COLUMN created_at datetime(6) NOT NULL DEFAULT NOW(6);
