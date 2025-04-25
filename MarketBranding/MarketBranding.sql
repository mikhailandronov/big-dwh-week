drop table if exists brand_data;
drop table if exists pickup_point;

-- Таблица с пунктами выдачи заказов (ПВЗ)
create table pickup_point (
	id bigint primary key, -- id ПВЗ
	branded_since date, -- дата брендирования: не null для брендированных ПВЗ и null в остальных случаях
	prev_id bigint unique -- идентификатор предыдущего ПВЗ (если было переоформление одного ПВЗ на другое)
);

-- Таблица содержит данные, что конкретному ПВЗ было начислено конкретное количество рублей за брендированность.
-- Брендированность = ПВЗ сделал ремонт и мы ему платим деньги
create table brand_data (
	pickup_point_id bigint primary key references pickup_point(id), -- id ПВЗ
	amount_rub bigint not null -- сумма, которую заплатили данному ПВЗ, всегда > 0
);

/*
insert into pickup_point
  (id, branded_since, prev_id)
values
  (1000001, date '2020-02-01', null);


insert into pickup_point
  (id, branded_since, prev_id)
values
  (1000002, date '2020-02-01', null);


insert into pickup_point
  (id, branded_since, prev_id)
values
  (1, date '2020-02-01', null),
  (2, date '2020-03-01', 1),
  (3, date '2020-04-01', 2);
*/


insert into pickup_point (id, branded_since, prev_id)
values
	(1, date '2020-02-01', null),
	(2, date '2020-03-01', 1),
	(3, date '2020-04-01', 2),
	(4, date '2020-02-01', null),
	(5, date '2020-03-01', 4),
	(6, date '2020-03-01', 5),
	(7, date '2020-03-01', 8),
	(8, date '2020-03-01', 7),
	(9, date '2020-03-01', 9)
;

insert into brand_data (pickup_point_id, amount_rub)
values
	(5, 10000);


-- select id from pickup_point where branded_since = :targetDate

select max (id) --, branded_since, payment
from
(
	with recursive pp_chain as
	(
		select
			id as root, id, prev_id, branded_since,
			array [id] as path,
			false as is_cycle
		from
			pickup_point
		where
			prev_id is null

	union all
		select
			ppc.root, pp.id, pp.prev_id, pp.branded_since,
			ppc.path || pp.id as path,
			pp.id = any(ppc.path) as is_cycle
		from
			pickup_point pp
			inner join pp_chain ppc on pp.prev_id = ppc.id
	)
	select
		ppc.root, ppc.id, ppc.prev_id, ppc.branded_since,
		ppc.path,
		ppc.is_cycle,
		bd.amount_rub,
		min (bd.amount_rub) over root_sequence as payment,
		row_number() over root_sequence as rownum
	from
		pp_chain ppc
		left join brand_data bd on bd.pickup_point_id = ppc.id
	window
		root_sequence as (
			partition by ppc.root
			order by ppc.branded_since, ppc.id
			rows between unbounded preceding and current row
		)
	order by
		root, id
) payments

where
	payment is null
	and branded_since = :targetDate
group by
	root

union
select id
from
	pickup_point pp
	left join brand_data bd on bd.pickup_point_id = pp.id
where
	pp.id = pp.prev_id
    and bd.amount_rub is null
    and branded_since = :targetDate