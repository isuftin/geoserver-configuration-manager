FROM kartoza/geoserver

MAINTAINER Ivan Suftin <isuftin@usgs.gov>

ENV GEOSERVER_VERSION=${GEOSERVER_VERSION:-2.8.1}

RUN rm /tmp/resources/geoserver.zip
RUN wget -c http://downloads.sourceforge.net/project/geoserver/GeoServer/$GEOSERVER_VERSION/geoserver-$GEOSERVER_VERSION-bin.zip -O /tmp/resources/geoserver.zip;
RUN rm -r /opt/geoserver
RUN unzip /tmp/resources/geoserver.zip -d /opt 
RUN mv -v /opt/geoserver* /opt/geoserver

CMD "/opt/geoserver/bin/startup.sh"