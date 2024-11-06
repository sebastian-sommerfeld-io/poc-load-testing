<!-- ---
hide:
  - navigation
--- -->

# PoC Load Testing

[file-issues]: https://github.com/sebastian-sommerfeld-io/poc-load-testing/issues

This project is a PoC for Load Testing with [Gatling](https://gatling.io).

Gatling provides a cloud-hosted web application <https://computer-database.gatling.io> for running sample simulations.

Gatling test simulations are defined in the `src` directory. To maintain a simple setup, the [JavaScript version of Gatling](https://docs.gatling.io/tutorials/scripting-intro-js) is used.

## Usage

Run either of the following commands from the project root to start the Gatling simulation:

```bash
docker compose up

npx gatling run --simulation <the_simulation>
```

For instructions on writing Gatling simulations, refer to the Gatling documentation:

- <https://docs.gatling.io/tutorials/scripting-intro-js>
- <https://docs.gatling.io/tutorials/advanced>

## Contact

Feel free to contact me via <sebastian@sommerfeld.io> or [raise an issue in this repository][file-issues].