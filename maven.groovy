def Compilacion() {
  chmod '+x mvnw'
  sh './mvnw clean compile -e'
}

def Test() {
  sh './mvnw clean test -e'
}

return this