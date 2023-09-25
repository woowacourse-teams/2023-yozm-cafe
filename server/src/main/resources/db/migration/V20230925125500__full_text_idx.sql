alter table cafe add fulltext index cafe_name_idx (name) with parser ngram;

alter table cafe add fulltext index address_idx (address) with parser ngram;

alter table menu add fulltext index menu_name_idx (name) with parser ngram;