// The module 'vscode' contains the VS Code extensibility API
// Import the module and reference it with the alias vscode in your code below
import * as vscode from "vscode";
import { Store } from "./store";

// this method is called when your extension is activated
// your extension is activated the very first time the command is executed
export async function activate(context: vscode.ExtensionContext) {
  // Use the console to output diagnostic information (console.log) and errors (console.error)
  // This line of code will only be executed once when your extension is activated
  // console.log(
  //   'Congratulations, your extension "tell-me-my-items" is now active!'
  // );

  // The command has been defined in the package.json file
  // Now provide the implementation of the command with registerCommand
  // The commandId parameter must match the command field in package.json

  // "commands": [
  // 	{
  // 		"command": "tell-me-my-items.helloWorld",
  // 		"title": "Hello World"
  // 	}
  // ]
  // context.subscriptions.push(vscode.commands.registerCommand('tell-me-my-items.helloWorld', () => {
  // 	// The code you place here will be executed every time your command is executed
  // 	// Display a message box to the user
  // 	vscode.window.showInformationMessage('YO');
  // }));
  const store = new Store();
  function reloadFromPath() {
    const path = vscode.workspace.getConfiguration("tmmi").path;
    store.loadItemsFromDisk(path);
  }
  const provider: vscode.CompletionItemProvider<vscode.CompletionItem> = {
    provideCompletionItems(document, position, token, context) {
      return store
        .getItems()
        .map((x) => ({ label: x })) as vscode.CompletionItem[];
    },
  };

  const langs = ["sfml","zenscript","javascript","typescript"];
  for (const language of langs) {
    context.subscriptions.push(
      vscode.languages.registerCompletionItemProvider(
        { language },
        provider
      )
    );
  }

  context.subscriptions.push(
    vscode.commands.registerCommand("tmmi.reloadFromPath", () =>
      reloadFromPath()
    )
  );
  context.subscriptions.push(
    vscode.commands.registerCommand("tmmi.disableSuggestions", () =>
      store.clearItems()
    )
  );

  reloadFromPath();
}

// this method is called when your extension is deactivated
export function deactivate() {}
