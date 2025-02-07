/*
 * Copyright 2023 OrdinaryRoad
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.ordinaryroad.barrage.fly.pojo.dto

import cn.hutool.core.bean.BeanUtil
import com.fasterxml.jackson.annotation.JsonProperty
import tech.ordinaryroad.barrage.fly.constant.BarrageFlyTaskStatusEnum
import tech.ordinaryroad.barrage.fly.context.BarrageFlyTaskContext
import tech.ordinaryroad.barrage.fly.dal.entity.BarrageFlyTaskDO

/**
 * @author mjz
 * @date 2023/9/10
 */
data class BarrageFlyTaskDTO(
    @JsonProperty("id")
    var uuid: String = "",
    var platform: String = "",
    var roomId: String = "",
    var remark: String? = null,
    var cookie: String? = null,
    var msgPreMapExpress: String? = null,
    var msgFilterExpress: String? = null,
    var msgPostMapExpress: String? = null,
    var socks5ProxyHost: String? = null,
    var socks5ProxyPort: Int? = null,
    var socks5ProxyUsername: String? = null,
    var socks5ProxyPassword: String? = null,
    var platformConfigJson: String? = null,
    var status: BarrageFlyTaskStatusEnum? = null,
) {
    companion object {
        fun BarrageFlyTaskDO.toDTO(ignoreExpress: Boolean = false): BarrageFlyTaskDTO {
            return if (ignoreExpress) {
                BeanUtil.copyProperties(
                    this,
                    BarrageFlyTaskDTO::class.java,
                    "msgPreMapExpress",
                    "msgFilterExpress",
                    "msgPostMapExpress"
                )
            } else {
                BeanUtil.copyProperties(this, BarrageFlyTaskDTO::class.java)
            }.apply {
                status = BarrageFlyTaskContext.getTaskStatus(uuid)
            }
        }
    }
}
