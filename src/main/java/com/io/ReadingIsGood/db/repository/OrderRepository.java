package com.io.ReadingIsGood.db.repository;

import com.io.ReadingIsGood.db.entity.Order;
import com.io.ReadingIsGood.vo.MonthlyStatistic;
import com.io.ReadingIsGood.vo.OrderStatisticsItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    Page<Order> findAllByOwnerId(UUID ownerId, Pageable pageable);

    List<Order> findAllByCreationDateBetween(Date startDate, Date endDate);



    @Query(value="select count(o) as \"Total Order Count\", sum(bo.count) as \"Total Book Count\", sum(o.totalprice) as \"Total Purchased Amount\", TO_CHAR(o.creationdate, 'Month') AS \"Month\" from public.order o, bookorder bo where bo.fk_order_id = o.id group by TO_CHAR(o.creationdate, 'Month')", nativeQuery = true)
    List<MonthlyStatistic> getMonthlyOrderStatistic();

}
