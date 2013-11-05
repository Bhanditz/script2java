from com.example import Iface

class Migration(Iface):
    def migrate(self, con1, con2):
        print(con1)
        print(con2)
        return "Migration done with conns: " + con1 + con2
