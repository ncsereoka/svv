#{{{ Marathon
from default import *
#}}} Marathon

def test():
    set_java_recorded_version("11.0.8")
    if window('Svvitch - [STOPPED]'):
        click('Start server')
    close()

    if window('Svvitch - [RUNNING]'):
        assert_p('lbl:running', 'Text', 'running')
        select('Switch to maintenance mode', 'true')
    close()

    if window('Svvitch - [MAINTENANCE]'):
        assert_p('lbl:maintenance', 'Text', 'maintenance')
        select('Switch to maintenance mode', 'true')
        select('Switch to maintenance mode', 'false')
    close()

    if window('Svvitch - [RUNNING]'):
        assert_p('lbl:running', 'Text', 'running')
        select('Switch to maintenance mode', 'false')
        click('Stop server')
    close()

    pass