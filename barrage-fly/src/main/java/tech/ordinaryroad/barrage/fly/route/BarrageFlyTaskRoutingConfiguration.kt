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

package tech.ordinaryroad.barrage.fly.route

import cn.dev33.satoken.stp.StpUtil
import cn.hutool.core.util.StrUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import tech.ordinaryroad.barrage.fly.handler.BarrageFlyTaskHandler
import tech.ordinaryroad.barrage.fly.property.ApiProperties

@Configuration(proxyBeanMethods = false)
class BarrageFlyTaskRoutingConfiguration(val apiProperties: ApiProperties) {

    @Bean
    fun taskRouterFunction(handler: BarrageFlyTaskHandler): RouterFunction<ServerResponse> {
        return coRouter {
            "${StrUtil.emptyIfNull(apiProperties.prefix)}/task".nest {
                before {
                    StpUtil.checkLogin()
                    it
                }
                POST("", ACCEPT_JSON, handler::createTask)
                DELETE("", handler::deleteTask)
                PUT("", ACCEPT_JSON, handler::update)
                PUT("/cookie", accept(MediaType.TEXT_PLAIN), handler::updateCookie)
                PUT("/validate", accept(MediaType.APPLICATION_JSON), handler::validate)
                PUT("/validate/express", accept(MediaType.APPLICATION_JSON), handler::validateExpress)
            }
        }
    }

    @Bean
    fun taskMonoRouterFunction(handler: BarrageFlyTaskHandler): RouterFunction<ServerResponse> {
        return router {
            "${StrUtil.emptyIfNull(apiProperties.prefix)}/task".nest {
                before {
                    StpUtil.checkLogin()
                    it
                }
                GET("/start", handler::start)
                GET("/stop", handler::stop)
            }
            "/task".nest {
                GET("", handler::getTask)
                GET("/status", handler::getTaskStatus)
                GET("/page/{offset}/{limit}/", handler::pageTask)
                GET("/statuses", handler::statuses)
                GET("/platform/options", handler::platformOptions)
                GET("/platform/configs", handler::platformConfigs)
            }
        }
    }

    companion object {
        private val ACCEPT_JSON = RequestPredicates.accept(MediaType.APPLICATION_JSON)
    }

}