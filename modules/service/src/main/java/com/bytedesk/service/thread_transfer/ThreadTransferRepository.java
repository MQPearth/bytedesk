/*
 * @Author: jackning 270580156@qq.com
 * @Date: 2024-06-23 10:16:30
 * @LastEditors: jackning 270580156@qq.com
 * @LastEditTime: 2025-02-21 15:44:00
 * @Description: bytedesk.com https://github.com/Bytedesk/bytedesk
 *   Please be aware of the BSL license restrictions before installing Bytedesk IM – 
 *  selling, reselling, or hosting Bytedesk IM as a service is a breach of the terms and automatically terminates your rights under the license.
 *  Business Source License 1.1: https://github.com/Bytedesk/bytedesk/blob/main/LICENSE 
 *  contact: 270580156@qq.com 
 *  联系：270580156@qq.com
 * Copyright (c) 2024 by bytedesk.com, All Rights Reserved. 
 */
package com.bytedesk.service.thread_transfer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ThreadTransferRepository extends JpaRepository<ThreadTransferEntity, Long>, JpaSpecificationExecutor<ThreadTransferEntity> {
    
    Optional<ThreadTransferEntity> findByUid(String uid);

    List<ThreadTransferEntity> findByOrgUidAndCreatedAtBetween(String orgUid, LocalDateTime startTime, LocalDateTime endTime);

    // List<ThreadTransferEntity> findByOrgUidAndDateAndHour(String orgUid, String date, int hour);

    // List<ThreadTransferEntity> findByOrgUidAndDate(String orgUid, String date);

    List<ThreadTransferEntity> findByOrgUid(String orgUid);
}
