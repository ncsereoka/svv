#{{{ Marathon
from default import *
#}}} Marathon

def test():
    set_java_recorded_version("11.0.8")
    if window('Svvitch - [STOPPED]'):
        assert_p('lbl:not running', 'Text', 'not running')
        assert_p('not running', 'Text', 'not running')
        assert_p('lbl:not running_2', 'Text', 'not running')
        assert_p('Server listening on port', 'Enabled', 'true')
        assert_p(',,,_2', 'Enabled', 'true')
        assert_p(',,,_3', 'Enabled', 'true')
        assert_p('Switch to maintenance mode', 'Enabled', 'false')
        click('Start server_2')
    close()

    if window('Svvitch - [RUNNING]'):
        assert_p('lbl:running', 'Enabled', 'true')
        assert_p('lbl:3000', 'Enabled', 'true')
        assert_p('Switch to maintenance mode', 'Text', 'false')
        assert_p('Server listening on port', 'Enabled', 'true')
        assert_p('Server listening on port', 'Text', '3000')
        assert_p(',,,_2', 'Enabled', 'false')
        assert_p(',,,_3', 'Enabled', 'true')
        assert_p('Switch to maintenance mode', 'Enabled', 'true')
        select('Switch to maintenance mode', 'true')
    close()

    if window('Svvitch - [MAINTENANCE]'):
        assert_p('lbl:maintenance', 'Text', 'maintenance')
        assert_p(',,,', 'Enabled', 'true')
        assert_p(',,,_2', 'Enabled', 'false')
        click('Stop server')
    close()


    pass
