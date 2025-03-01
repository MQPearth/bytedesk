/*
 * @Author: jackning 270580156@qq.com
 * @Date: 2024-09-10 09:52:44
 * @LastEditors: jackning 270580156@qq.com
 * @LastEditTime: 2024-10-22 12:15:58
 * @Description: bytedesk.com https://github.com/Bytedesk/bytedesk
 *   Please be aware of the BSL license restrictions before installing Bytedesk IM – 
 *  selling, reselling, or hosting Bytedesk IM as a service is a breach of the terms and automatically terminates your rights under the license.
 *  Business Source License 1.1: https://github.com/Bytedesk/bytedesk/blob/main/LICENSE 
 *  contact: 270580156@qq.com 
 *  联系：270580156@qq.com
 * Copyright (c) 2024 by bytedesk.com, All Rights Reserved. 
 */
package com.bytedesk.core.socket.connection;

import com.bytedesk.core.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * TODO: 持久化，当前客户端保持的长链接，
 * 1. 便于在手机端查看pc客户端登录情况，
 * 2. 在pc客户端查看手机端登录情况
 * http://127.0.0.1:9003/mqtt/api/v1/clientIds
 * 
 * @{MqttController}
 */
@Data
@Entity
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bytedesk_core_connection")
public class ConnectionEntity extends BaseEntity {

    // 
    
    
    private String userUid;
}
