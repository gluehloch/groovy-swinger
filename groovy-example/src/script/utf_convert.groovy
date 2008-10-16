
println 'Hallo Herr Winkler'
def cmd = [ 'iconv', '-f', 'Cp1252', '-t', 'UTF-8', 'datei.txt' ]
Process ps = Runtime.getRuntime().exec(cmd)
def err = ps.waitFor()

println "Status: ${err}"
