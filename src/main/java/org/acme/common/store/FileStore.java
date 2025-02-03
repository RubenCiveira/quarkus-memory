/* @autogenerated */
package org.acme.common.store;

import java.io.IOException;
import java.util.Optional;

/**
 * Una interfaz para manejar el almacenamiento de ficheros en un repositorio.
 * 
 */
public interface FileStore {
  /**
   * Copia el stream de la fuente de datos en un soporte temporal, e informa de una clave temporal
   * para localizar posteriormente el fichero.
   * 
   * El soporte temporal se podrá borrar cada cierto tiempo, con lo que el almacenamiento en este
   * soporte se deberá utilizar para operaciones muy cortas.
   * 
   * @param source La fuente de datos que copiar
   * @return El código temporal para ubicar el fichero en el repositorio
   */
  RepositoryLink tempStore(BinaryContent source);

  /**
   * Recupera una response entity de http para descargar un fichero desde una url temporal. El
   * repositorio borra los ficheros temporales cada cierto tiempo.
   * 
   * @param path la url agnostica
   * @return la respuesta http
   * @throws IOException si hay problemas a la hora de acceder al fichero
   */
  Optional<BinaryContent> retrieveTemp(String pathParam);

  /**
   * Recupera una fuente de datos de la que leer el contenido de un fichero.
   * 
   * @param path El path del fichero a leer
   * @param temporal true cuando queremos leer un fichero temporal, o false para leer ficheros
   *        definitivos
   * @return Una fuente de datos con un inputStream para leer el fichero DataSource retrieve(String
   *         path, boolean temporal);
   */
  Optional<BinaryContent> retrieveFile(String key);

  /**
   * Coge un fichero temoral del repositorio y lo guarda de manera definitiva.
   * 
   * Los ficheros guardados de manera definitiva no se borran del repositorio, salvo que se indique
   * de forma explicita).
   * 
   * @param path La ruta al fichero temporal
   * @return La ruta del fichero en el almacen definitivo
   */
  RepositoryLink commitContent(String key);

  /**
   * Coge un fichero temoral del repositorio y lo guarda de manera definitiva.
   * 
   * Los ficheros guardados de manera definitiva no se borran del repositorio, salvo que se indique
   * de forma explicita).
   * 
   * @param path La ruta al fichero temporal
   * @return La ruta del fichero en el almacen definitivo
   */
  RepositoryLink commitReplace(String key, RepositoryLink link);

  /**
   * Fichero
   * 
   * @param key
   * @param content
   */
  RepositoryLink replaceContent(String key, BinaryContent content);

  /**
   * Borra un fichero del repositorio
   * 
   * @param path El path del fichero a borrar
   */
  void deleteFile(String key);
}
