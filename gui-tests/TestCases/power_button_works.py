#{{{ Marathon
from default import *
#}}} Marathon

def test():
    set_java_recorded_version("11.0.8")
    if window('Svvitch - [STOPPED]'):
        assert_p('lbl:not running', 'Text', 'not running')
        click('Start server_2')
    close()

    if window('Svvitch - [RUNNING]'):
        assert_p('lbl:running', 'Text', 'running')
        click('Stop server')
    close()

    if window('Svvitch - [STOPPED]'):
        assert_p('lbl:not running', 'Text', 'not running')
    close()

    pass
