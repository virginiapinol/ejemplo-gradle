def Compilacion() {
  sh './gradle build'
}
def Test(){
  sh './gradle test'
}

return this