# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
FROM eclipse-temurin:8-jre

ENV DOCKER true
ENV TZ Asia/Shanghai
ENV SOLIDUI_HOME /opt/solidui

WORKDIR $SOLIDUI_HOME

COPY ./bin/* $SOLIDUI_HOME/bin/

COPY ./conf/* $SOLIDUI_HOME/conf/

COPY ./libs/* $SOLIDUI_HOME/libs/


EXPOSE 12345 12345

CMD [ "/bin/sh", "-c", "$SOLIDUI_HOME/bin/docker-start.sh" ]