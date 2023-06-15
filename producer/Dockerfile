FROM selenium/standalone-firefox:114.0

ENV PATH=$PATH:/opt/java/jdk-20/bin

WORKDIR /opt/java

RUN curl https://download.oracle.com/java/20/archive/jdk-20_linux-x64_bin.tar.gz -o jdk-20_linux-x64_bin.tar.gz
RUN tar -xzf jdk-20_linux-x64_bin.tar.gz  && rm -rf jdk-20_linux-x64_bin.tar.gz

COPY target/producer-0.0.1-SNAPSHOT.jar producer.jar
COPY entry_point_commands.sh ../bin/entry_point_commands.sh
CMD ["/opt/bin/entry_point_commands.sh"]