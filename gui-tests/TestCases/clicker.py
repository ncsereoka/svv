#{{{ Marathon
from default import *
#}}} Marathon

def test():
    set_java_recorded_version("11.0.8")

    for i in range(100):
	    if window('Svvitch - [STOPPED]'):
	        click('Start server_2')
	    close()
	
	    if window('Svvitch - [RUNNING]'):
	        click('Stop server')
	    close()

    pass