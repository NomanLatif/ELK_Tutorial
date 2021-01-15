# ELK Tutorial
How to perform centralize logging in microservice architecture using ELK Stack

Code:
This is a simple spring boot application that has one rest resource http://localhost:9898/getUser/[id]
It uses logback to send data to logstash listening on port 5044


###### Download ELK Binary Distrubution
Configure Logstash:

	* mkdir -p ~/settings/elastic/logstash/pipeline/
	* create logstash config file
	cat > ~/settings/elastic/logstash/pipeline/logstash.conf << EOF
	#
	input {
  		tcp {
    			port => 5044
    			codec => json_lines
  		}
	}

	filter {
      		json {
        		source => "message"
      		}
      		mutate {
			remove_field => ["message", "@version", "logger_name", "host", "port", "level", "level_value", "thread_name"]
      		}
    	}

	output {
  		stdout { codec => rubydebug }
  		elasticsearch {
    			hosts => [ "elasticsearch:9200" ]
    			index => "noman_drop-%{+YYYY.MM.dd}"
  		}
	}
	#

	* docker pull docker.elastic.co/logstash/logstash-oss
	* docker run -d --name logstash --restart=always --net elastic -p 5044:5044 -v ~/settings/elastic/logstash/pipeline/:/usr/share/logstash/pipeline/ docker.elastic.co/logstash/logstash-oss

###### 1.Elastic Search [Download](https://www.elastic.co/downloads/elasticsearch).
###### 2.Logstash [Download](https://www.elastic.co/downloads/kibana).
###### 3.Kibana [Download](https://artifacts.elastic.co/downloads/logstash/logstash-7.6.2.zip).
