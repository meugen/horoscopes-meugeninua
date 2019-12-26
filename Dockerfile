FROM store/oracle/serverjre:8

ADD target/universal/horoscopes-meugeninua-1.0-SNAPSHOT.txz /opt
EXPOSE 9000
CMD ["/opt/horoscopes-meugeninua-1.0-SNAPSHOT/bin/horoscopes-meugeninua"]